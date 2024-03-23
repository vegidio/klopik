package main

/*
#include <stdlib.h>
*/
import "C"

import (
    "bytes"
    "encoding/json"
    "fmt"
    "net/http"
)

func createRequest(method *C.char, url *C.char, body *C.char, headers *C.char) *http.Request {
	gMethod := C.GoString(method)
	gUrl := C.GoString(url)
	var req *http.Request

	// Body
	if body == nil {
		req, _ = http.NewRequest(gMethod, gUrl, nil)
	} else {
		gBody := []byte(C.GoString(body))
		req, _ = http.NewRequest(gMethod, gUrl, bytes.NewBuffer(gBody))
	}

	// Headers
	if headers != nil {
		gHeaders := getRequestHeaders(headers)
		for key, value := range gHeaders {
			req.Header.Set(key, value)
		}
	}

	return req
}

func getRequestHeaders(headers *C.char) map[string]string {
	gHeaders := C.GoString(headers)

	result := make(map[string]interface{})
	err := json.Unmarshal([]byte(gHeaders), &result)
	if err != nil {
		return map[string]string{}
	}

	stringMap := make(map[string]string)
	for key, value := range result {
		stringMap[key] = fmt.Sprintf("%v", value)
	}

	return stringMap
}

func getResponseHeaders(headers map[string][]string) *C.char {
	byteHeaders, err := json.Marshal(headers)
	if err != nil {
		return nil
	}

	jsonHeaders := string(byteHeaders)
	return C.CString(jsonHeaders)
}