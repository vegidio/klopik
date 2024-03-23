package main

import "C"
import (
	"fmt"
	"unsafe"
)

func testRequest() {
	cMethod := C.CString("GET")
	cUrl := C.CString("https://httpbin.org/get")
	body, size, httpCode, headers, err := Request(cMethod, cUrl, nil, nil)

	fmt.Println("Body:", string(C.GoBytes(body, size)))
	fmt.Println("Size:", size)
	fmt.Println("HTTP Code:", httpCode)
	fmt.Println("Headers:", C.GoString(headers))
	fmt.Println("Error:", C.GoString(err))
}

func testStream() {
	callback := func(stream unsafe.Pointer, size C.int) {
		chunk := C.GoBytes(stream, size)
		fmt.Println(string(chunk))
	}

	cMethod := C.CString("GET")
	cUrl := C.CString("https://httpbin.org/get")
	size, httpCode, headers, err := Stream(cMethod, cUrl, nil, nil, unsafe.Pointer(&callback))

	fmt.Println("Size:", size)
	fmt.Println("HTTP Code:", httpCode)
	fmt.Println("Headers:", C.GoString(headers))
	fmt.Println("Error:", C.GoString(err))
}
