java -Xms512m -Xmx1024m -XX:PermSize=128m -XX:MaxPermSize=512m -jar agentManageJob.jar >/dev/null 2>&1 &
tail -f /home/tomcat/agentManager-job/agentManager-job-info.log