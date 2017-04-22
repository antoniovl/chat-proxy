# chat-proxy

This project illustrates basic concepts of client-server network programming. I used it as part of my didactic material 
for my Distributed Systems class. 

The project consists of 3 modules:
* chat-proxy-shared, with common classes used on the client and the server.
* chat-proxy-server: Server for the chat service. This may be incorrectly named "proxy" as is no real proxy. It just accepts
new clients, and when 2 peers want to communicate it will pipe their input/output.
* chat-proxy-client: A small Swing app to communicate with the server.

You need to run first the chat-proxy-server, then start the chat-proxy-client with the server's IP address as argument. 
Once you have 2 clients running, in one of them enter the other peer´s ip address and press "Start Chat with IP Addr".
This will initiate a connection, then enter text in the input text and press "Send". You should see your text in the other
client's screen.

&copy; CopyRight Antonio Varela.
Released under the MIT Licence.
