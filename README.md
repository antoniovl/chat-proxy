# Chat Proxy

This project was developed as an **educational resource** for the course **Network Programming** in the **Software Engineering** program at **ITESM (Instituto Tecnológico y de Estudios Superiores de Monterrey)**.

It demonstrates core concepts of **client-server communication** using **Java sockets**, including session management, message routing, and basic concurrency handling.

---

## Project Structure

The project is organized into three modules:

- `chat-proxy-shared`: Shared classes used by both client and server (e.g., message models, protocol definitions).
- `chat-proxy-server`: Server application that manages client connections and routes messages between clients.
- `chat-proxy-client`: Simple Swing-based client application to connect and exchange messages through the server.

---

## Main Features

- TCP-based communication between server and clients
- Simple message routing by user session
- Basic concurrency for handling multiple client connections
- Minimal Swing GUI for user interaction
- Designed for educational and demonstration purposes

---

## Technologies

- Java Standard Edition
- Java Sockets
- Java Swing (for the client GUI)

---

## Getting Started

### Prerequisites

- Java Development Kit (JDK) 8 or higher
- Maven (optional, if you want to build from the command line)

### Build and Run

1. **Clone the repository:**

   ```bash
   git clone https://github.com/antoniovl/chat-proxy.git
   cd chat-proxy
   ```

2. **Build the project:**

   If you are using Maven:

   ```bash
   mvn clean install
   ```

3. **Run the Server:**

   From the `chat-proxy-server/target` directory (or your IDE):

   ```bash
   java -jar chat-proxy-server-*.jar
   ```

   Or run the main class directly from your IDE:  
   `mx.icon.chatproxy.server.Main`

4. **Run the Client:**

   From the `chat-proxy-client/target` directory (or your IDE):

   ```bash
   java -jar chat-proxy-client-*.jar <server-ip-address>
   ```

   Or run the main class directly from your IDE:  
   `mx.icon.chatproxy.client.ChatJFrame`, passing the server's IP address as an argument.

5. **Connect Clients:**

   - Start with two clients.
   - In one of the clients, enter the other client's IP address and press **"Start Chat with IP Addr"**.
   - Once the connection is established, type a message into the input field and press **"Send"**.
   - The message should appear on the other client's screen.

---

## Notes

- The server must be started before connecting clients.
- Clients must connect to the server using its IP address and a predefined TCP port. You can adjust the port configuration in the source code if needed.

---

## Purpose

This project was created to help undergraduate students:

- Understand low-level networking concepts through practical examples
- Build intuition around TCP connections, sessions, and message routing
- Gain experience with basic concurrent server design
- Connect theory from lectures with hands-on programming exercises

---

## License

This project is licensed under the [MIT License](LICENSE).

---

## Acknowledgments

Developed as part of the **Network Programming** course in the **Software Engineering** career at **ITESM**.

