package main

/*
#include <stdlib.h>
*/
import "C"

func main() {}

//export Get
func Get(url *C.char, body *C.char, headers *C.char) (*C.char, *C.char) {
	gUrl := C.GoString(url)
	request := createRequest(body, headers)
	resp, err := request.Get(gUrl)

	if err != nil {
		cErr := C.CString(err.Error())
		return nil, cErr
	}

	cResp := C.CString(resp.String())
	return cResp, nil
}

//export Post
func Post(url *C.char, body *C.char, headers *C.char) (*C.char, *C.char) {
	gUrl := C.GoString(url)
	request := createRequest(body, headers)
	resp, err := request.Post(gUrl)

	if err != nil {
		cErr := C.CString(err.Error())
		return nil, cErr
	}

	cResp := C.CString(resp.String())
	return cResp, nil
}
