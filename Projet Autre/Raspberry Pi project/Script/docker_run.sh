docker stop $(docker ps)
docker rm IUT_Assistant
docker run --name IUT_Assistant \
  --memory="64m" --memory-swap="128m" \
  --restart unless-stopped \
  -e DISCORD_TOKEN=XXXX \
  -v /home/node/IUT_Assistant/log:/home/node/IUT_Assistant/log \
  -v /home/node/IUT_Assistant/data:/home/node/IUT_Assistant/data \
  -p 49160:8080 \
  -d apyo37/iut_assistant
docker logs -f IUT_Assistant