# AI Consumption Platform Backend

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
