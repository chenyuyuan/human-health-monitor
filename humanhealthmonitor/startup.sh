#!/bin/sh
nohup java -jar /root/yuan/human-health-monitor/humanhealthmonitor/target/humanhealthmonitor-0.0.1-SNAPSHOT.war >/root/yuan/log/humanhealthmonitor-0.0.1-SNAPSHOT.out 2>&1 &
