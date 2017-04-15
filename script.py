#!usr/bin/ python
from crontab import CronTab


cron = CronTab(user=True)
job = cron.new(command='python /home/pi/code/server.py')

# def add_minutely(name, user, command, environment=None):
#     cron_job = cron.new(command=command, user=user)
#     cron_job.minute.every(1)
#     cron_job.enable()
#     cron.write()
#     if cron.render():
#         print cron.render()
#         return True

# def add_daily(name, user, command, environment=None):
#     cron_job = cron.new(command=command, user=user)
#     cron_job.minute.on(0)
#     cron_job.hour.on(0)
#     cron_job.enable()
#     cron.write()
#     if cron.render():
#         print cron.render()
#         return True

job.minute.every(1)

#data = urllib2.download("http://chrisdesoto.me:7070/schedule/1"
#print("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~")
#print(data.read())

cron.write()