# Use a smaller base image
FROM --platform=linux/arm64 node:alpine

# Set environment variables
ENV NODE_ENV=production
ENV TZ=Europe/Paris

# Create app directory
WORKDIR /home/node/IUT_Assistant

# Copy package.json and package-lock.json first to leverage Docker cache
COPY package*.json ./

# Install dependencies
RUN npm ci --only=production

# Prune unused dependencies
RUN npm prune --production

# Create necessary directories and set ownership to node user
RUN mkdir -p /home/node/IUT_Assistant/log /home/node/IUT_Assistant/data && \
    if ! id node >/dev/null 2>&1; then \
        adduser -D -H -u 1000 node; \
    fi && \
    chown -R node:node /home/node/IUT_Assistant && \
    chmod -R 700 /home/node/IUT_Assistant/log /home/node/IUT_Assistant/data

# Copy the rest of the application code
COPY --chown=node:node . .

# Switch to non-root user
USER node

# Define volumes for log and data
VOLUME /home/node/IUT_Assistant/log
VOLUME /home/node/IUT_Assistant/data

# Expose the port the app runs on
EXPOSE 8080

# Start the application
CMD ["node", "index.js"]