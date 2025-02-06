docker stop $(docker ps)
docker rmi -f $(docker images -a -q)
docker rm DiscordBot
docker pull apyo37/iut-bot:latest
docker run --name DiscordBot -e DISCORD_TOKEN=MTI4NzQ3MjgzNDIyNjAzMjY5MQ.GdApoM.oLv4graIDkum91yHCgqpJekeBnqSgQR_xZAngQ -v ./DockerBot/log:/home/Grace/DockerBot/log -v ./DockerBot/data:/home/Grace/DockerBot/data -p 49160:8080 -d apyo37/iut-bot
docker logs -f DiscordBot