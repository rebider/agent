java -Xms800m -Xmx800m -XX:PermSize=256m -XX:MaxPermSize=512m -XX:MaxNewSize=512m -jar agentManagejob.jar >/dev/null 2>&1 &
tail -f /home/ryx/agentManager-job-dubbo/agentManager-job-debug.log