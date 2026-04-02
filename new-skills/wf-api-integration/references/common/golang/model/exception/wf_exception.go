package exception

import "fmt"

// WfException represents a WorldFirst API exception
type WfException struct {
	Code    WfErrorCode
	Message string
}

func (e *WfException) Error() string {
	return fmt.Sprintf("WF Error [%s]: %s", e.Code, e.Message)
}

// NewWfException creates a new WfException
func NewWfException(code WfErrorCode, message string) *WfException {
	return &WfException{Code: code, Message: message}
}

