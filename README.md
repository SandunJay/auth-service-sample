<h1>Authentication Verification Boilerplate</h1>

<p>In today's digital landscape, robust authentication mechanisms are crucial to securing applications. The <strong>Authentication Verification Boilerplate</strong> offers a comprehensive, scalable solution for implementing advanced authentication systems using modern technologies.</p>

<h2>Overview</h2>
<p>This boilerplate leverages <strong>Node.js</strong>, <strong>Express.js</strong>, and <strong>MongoDB</strong>, providing essential features like user registration, email verification, JWT handling, and password management. It also includes advanced functionalities like multi-factor authentication (MFA), token revocation, and user profile management, ensuring a complete security framework.</p>

<h2>Features</h2>

<h3>User Authentication</h3>
<ul>
  <li><strong>Registration</strong>: Email/password registration with email verification.</li>
  <li><strong>Login</strong>: JWT-based session management.</li>
  <li><strong>OTP Authentication</strong>: Supports OTP-based login for enhanced security.</li>
</ul>

<h3>Password Management</h3>
<ul>
  <li><strong>Forgot Password</strong>: Request password reset emails.</li>
  <li><strong>Reset Password</strong>: Token-based password reset functionality.</li>
  <li><strong>Password Hashing</strong>: Uses <code>bcrypt</code> for secure password storage.</li>
</ul>

<h3>Token Management</h3>
<ul>
  <li><strong>JWT</strong>: Access and refresh tokens for session management.</li>
  <li><strong>Token Revocation</strong>: Users can revoke tokens to end sessions.</li>
  <li><strong>Token Expiration</strong>: Manages token lifetimes for enhanced security.</li>
</ul>

<h3>Security</h3>
<ul>
  <li><strong>Rate Limiting</strong>: Protects against brute force attacks.</li>
  <li><strong>IP Blocking</strong>: Blocks suspicious IP addresses.</li>
  <li><strong>2FA</strong>: Integrates two-factor authentication using <code>speakeasy</code>.</li>
</ul>

<h3>User Management</h3>
<ul>
  <li><strong>Profile Management</strong>: Retrieve and update user profiles.</li>
  <li><strong>Account Deletion</strong>: Allows users to delete their accounts.</li>
</ul>

<h3>Monitoring & Observation</h3>
<ul>
  <li><strong>Prometheus</strong> & <strong>Grafana</strong>: Integrated for monitoring, alerting, and visualizing metrics.</li>
</ul>

<h3>Containerization & Orchestration</h3>
<ul>
  <li><strong>Docker</strong> & <strong>Docker Compose</strong>: Easily set up and manage services in containers.</li>
</ul>

<h2>File Structure</h2>

<pre><code>
auth-verification-server/
│
├── config/           # Configuration files
├── controllers/      # User and authentication controllers
├── models/           # MongoDB models
├── routes/           # API routes
├── utils/            # Utility functions
└── tests/            # Unit and integration tests
</code></pre>

<h2>Technologies Used</h2>
<ul>
  <li><strong>Node.js</strong>: Backend runtime.</li>
  <li><strong>Express.js</strong>: Web framework.</li>
  <li><strong>MongoDB</strong>: NoSQL database.</li>
  <li><strong>Mongoose</strong>: MongoDB ODM.</li>
  <li><strong>JWT</strong>: Token-based authentication.</li>
  <li><strong>Redis</strong>: Session management and rate limiting.</li>
  <li><strong>Jest</strong>: Testing framework.</li>
</ul>

<h2>Setup Instructions</h2>

<h3>Prerequisites</h3>
<ul>
  <li><strong>Node.js</strong> (>= 16.0.0)</li>
  <li><strong>MongoDB</strong></li>
  <li><strong>Redis</strong></li>
</ul>

<h3>Installation</h3>
<ol>
  <li>Clone the repository:</li>
  <pre><code>git clone https://github.com/SandunJay/auth-verification-boilerplate.git
cd auth-verification-server
</code></pre>
  <li>Install dependencies:</li>
  <pre><code>npm install</code></pre>
  <li>Set up environment variables in a <code>.env</code> file:</li>
  <pre><code>
PORT=5555
JWT_SECRET=your_jwt_secret
JWT_REFRESH_SECRET=your_refresh_jwt_secret
MONGO_URI=mongodb://localhost:27017/authDB
REDIS_URL=redis://localhost:6379
</code></pre>
  <li>Start the server:</li>
  <pre><code>npm start</code></pre>
</ol>

<p>The server will run on <code>http://localhost:5555</code>.</p>

<h2>API Endpoints</h2>
<ul>
  <li><strong>Registration</strong>: <code>POST /api/auth/register</code></li>
  <li><strong>Login</strong>: <code>POST /api/auth/login</code></li>
  <li><strong>Email Verification</strong>: <code>GET /api/auth/verify/:token</code></li>
  <li><strong>OTP Verification</strong>: <code>POST /api/auth/otp</code></li>
  <li><strong>Refresh Token</strong>: <code>POST /api/auth/refresh-token</code></li>
  <li><strong>Password Reset</strong>: <code>POST /api/auth/reset-password/:token</code></li>
  <li><strong>Profile Management</strong>: <code>GET /api/user/profile</code></li>
  <li><strong>Account Deletion</strong>: <code>DELETE /api/user/delete</code></li>
</ul>

<h2>Testing</h2>
<p>Run tests using:</p>
<pre><code>npm test</code></pre>

<h2>Contributing</h2>
<p>Contributions are welcome! Please fork the repository, create a new branch, and submit a pull request.</p>

