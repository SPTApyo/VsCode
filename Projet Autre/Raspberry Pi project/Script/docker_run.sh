docker stop $(docker ps)
docker rm DiscordBot
docker run --name DiscordBot -e DISCORD_TOKEN=XXXX -v ./DockerBot/log:/home/Grace/DockerBot/log -v ./DockerBot/data:/home/Grace/DockerBot/data -p 49160:8080 -d apyo37/iut-bot
docker logs -f DiscordBot