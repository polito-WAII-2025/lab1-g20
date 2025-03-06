# Use a Node.js base image for building
FROM node:18-alpine AS builder

# Set the working directory inside the container
WORKDIR /app

# Copy the package.json and package-lock.json files
COPY package*.json ./

# Install the dependencies
RUN npm install

# Copy everything *except* what's in .dockerignore
COPY . .


# Build the application
RUN npm run build

# Use a smaller image for the production build
FROM nginx:alpine

# Copy the contents of the dist folder
COPY --from=builder /app/dist /usr/share/nginx/html

# Copy the leaflet images (markers, etc.)
COPY --from=builder /app/node_modules/leaflet/dist/images /usr/share/nginx/html

# Copy the custom nginx configuration file
COPY nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 80

#ENTRYPOINT ["/docker-entry.sh"]
CMD ["nginx", "-g", "daemon off;"]