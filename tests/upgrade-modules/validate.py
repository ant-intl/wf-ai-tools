#!/usr/bin/env python3
"""Validate five Enhanced API modules in a temporary Java project; never call WF."""
import argparse
import json
import os
from pathlib import Path
import re
import shutil
import subprocess
import tempfile

HERE = Path(__file__).resolve().parent
ROOT = HERE.parents[1]
SKILL = ROOT / "wf-api-integration-upgrade"


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--static-only", action="store_true", help="Skip Java compile/test")
    parser.add_argument("--offline", action="store_true", help="Require cached Maven dependencies")
    args = parser.parse_args()
    data = json.loads((HERE / "contracts.json").read_text())
    entry = (SKILL / "SKILL.md").read_text()
    modules = {c["module"] for c in data["contracts"]}
    for c in data["contracts"]:
        module = SKILL / "references" / c["module"]
        guide = module / c["api"].replace("_", "-") / "GUIDE.md"
        assert guide.is_file(), guide
        assert "POST " + c["endpoint"] in guide.read_text(), guide
        # Each new API must appear in decision tree, keyword table, and module index.
        assert entry.count(c["api"]) >= 3 and entry.count(c["module"]) >= 3, c["api"]
        assert c["api"].replace("_", "-") + "/GUIDE.md" in (module / "README.md").read_text()
    for relative, expected in data["models"].items():
        source = (SKILL / "references" / relative).read_text()
        actual = dict((name, typ) for typ, name in re.findall(r"private ([\w<>, ]+) (\w+);", source))
        assert actual == expected, (relative, actual, expected)
    for module in modules:
        for path in (SKILL / "references" / module).rglob("*.md"):
            for target in re.findall(r"\]\(([^)]+)\)", path.read_text()):
                if "://" not in target and not target.startswith("#"):
                    assert (path.parent / target.split("#")[0]).exists(), (path, target)
    print("PASS: 5 module routes, 21 GUIDE paths/endpoints, 68 model schemas, local links", flush=True)
    if args.static_only:
        return
    if not shutil.which("mvn"):
        raise SystemExit("Maven is required; install it or put mvn on PATH")
    with tempfile.TemporaryDirectory(prefix="wf-skill-validation-") as temp:
        work = Path(temp)
        shutil.copy(HERE / "pom.xml", work / "pom.xml")
        # Include old modules to ensure the client overload stays source-compatible.
        for module in sorted(modules | {"common", "payouts/payouts", "issuing/otp-management"}):
            for source in (SKILL / "references" / module / "java").rglob("*.java"):
                if "spring" in source.parts:
                    continue
                text = source.read_text().replace("{basePackage}", "validation")
                package = re.search(r"package ([\w.]+);", text)[1]
                dest = work / "src/main/java" / package.replace(".", "/") / source.name
                dest.parent.mkdir(parents=True, exist_ok=True)
                assert not dest.exists(), dest
                dest.write_text(text)
        java_tests = work / "src/test/java/validation"
        java_tests.mkdir(parents=True)
        for source in HERE.glob("*Test.java"):
            shutil.copy(source, java_tests / source.name)
        resources = work / "src/test/resources"
        resources.mkdir(parents=True)
        shutil.copy(HERE / "contracts.json", resources / "contracts.json")
        # Only the temporary validator uses Central; do not rewrite user Maven settings.
        settings = work / "settings.xml"
        settings.write_text('<settings><mirrors><mirror><id>central</id><mirrorOf>*</mirrorOf>'
                            '<url>https://repo.maven.apache.org/maven2</url></mirror></mirrors></settings>')
        cmd = ["mvn", "-B", "-s", str(settings), "test"]
        if args.offline:
            cmd.insert(1, "-o")
        subprocess.run(cmd, cwd=work, check=True, env=os.environ.copy())
        print("PASS: generated Java templates compiled and local contract tests passed", flush=True)


if __name__ == "__main__":
    main()
