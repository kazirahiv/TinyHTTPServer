# Tiny HTTP Server 

> A JSON based HTTP server, Built with TCP/IP Sockets in Java.

So the general idea for this was mimmicking our traditional http transactions by implementing a web server using TCP/IP sockets.    
There will be our TinyHTTPServer project which will handle our request which is basically a JSON payload, And we will make request through another project which is Client, that will help by sending predefined commands for traditional http methods ex: GET/POST/PUT/DELETE and the command will generate a Json payload to request our server. It also handles concurrent requests using threads.


## Usage

```
We will be using `Intellij Idea Community` IDE  to open/build and run these projects. Thats all.
```

## Usage example (Client)

|  Request Type    	|    Arguments    	|                                                              Meaning                                                             	|              Example              	|
|------------------	|:---------------:	|:--------------------------------------------------------------------------------------------------------------------------------:	|:---------------------------------:	|
|     connect      	|     ip  port    	|                      connect to a server with      the IP address <ip> and        port address <port>                            	|     connect 192.168.0.10 2000     	|
|      get         	|      target     	|                                  retrieve the content of the specified web page from the server                                  	|          get /index.html          	|
|      put         	|  source  target 	| upload the content of the specified <source> web page on the client file system to the specified <target> web page on the server 	| put test.html /finance/index.html 	|
|     delete       	|      target     	|                          delete the specified web page from the server. <target> can be an empty folder                          	|     delete /finance/test.html     	|
|   disconnect     	|                 	|                                                    Disconnects from the server                                                    	|             disconnect            	|


## Request/Response Payload Example

```
Request 
-------------
{
  "message":"request",
  "type":"GET" ,
  "target":"relative path to the web page"
}

{
  "message":"request",
  "type":"PUT" ,
  "target":"relative path to the web page",
  "content":"web page content in utf-8 format"
}

{
  "message":"request",
  "type":"DELETE" ,
  "target":"/index.hmtl"
}

{
  "message":"request",
  "type":"DISCONNECT"
}


Response
-------------
{
"message":"response",
"statuscode":"a valid JSON number data type" ,
"content":"a valid JSON string data type"
}


```

## Usage Sample

![Alt text](sample.png?raw=true "Sample")


