FROM --platform=linux/arm64 node:lts-bookworm-slim

ENV NODE_ENV=production
ENV TZ=Europe/Paris

#USER node

COPY --chown=node:node . /home/Grace/DockerBot

WORKDIR /home/Grace/DockerBot

COPY package*.json ./

RUN npm ci --only=production

COPY . .

# Définir des volumes pour log et data
VOLUME /home/Grace/DockerBot/log
VOLUME /home/Grace/DockerBot/data

EXPOSE 8080
CMD ["node", "index.js"]