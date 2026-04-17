# AI Consumption Platform Backend

## Proxy Pattern Explanation

This project is a real Proxy Pattern example because the controller does not call the real AI generation service directly. Instead, it depends on the proxy chain:

`RateLimitProxyService -> QuotaProxyService -> MockAIGenerationService`

### Roles in the pattern

- `AIGenerationService` is the common interface shared by the real subject and both proxies.
- `MockAIGenerationService` is the real subject. It simulates text generation and token consumption.
- `RateLimitProxyService` is the first proxy. It checks the per-minute request limit before delegating.
- `QuotaProxyService` is the second proxy. It checks the monthly token quota before delegating.

### Why this is not just controller logic

The access-control rules are not implemented as `if` statements in the controller. They are encapsulated inside proxy services that implement the same interface as the real subject and delegate to another `AIGenerationService`. This makes the Proxy Pattern visible in the structure of the code, not only in comments.

## Deployment on Render

This backend is ready to run on Render using Docker.

### Required environment variables

- `PORT`
- `ALLOWED_ORIGIN`

### Example values

- `PORT=10000`
- `ALLOWED_ORIGIN=https://your-frontend.vercel.app`

### Render steps

1. Create a new **Web Service** on Render.
2. Connect the repository that contains the `BACKEND` folder.
3. Set the service root directory to `BACKEND`.
4. Choose **Docker** as the environment.
5. Add the required environment variables:
   - `PORT`
   - `ALLOWED_ORIGIN`
6. Deploy the service.

The application reads `PORT` through Spring Boot configuration and starts the containerized backend on that port. CORS allows requests from the frontend origin configured in `ALLOWED_ORIGIN`.
