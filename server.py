#!usr/bin/ python

def main():
    
    import RPi.GPIO as GPIO
    import time, urllib2

    #512 one full rotaion at delay 1
     
    GPIO.setmode(GPIO.BOARD)
    GPIO.setwarnings(False)
    coil_A_1_pin = 11 # pink
    coil_A_2_pin = 13 # orange
    coil_B_1_pin = 15 # blue
    coil_B_2_pin = 12 # yellow
     
    # adjust if different
    StepCount = 8
    Seq = range(0, StepCount)
    Seq[0] = [1,0,0,0]
    Seq[1] = [1,1,0,0]
    Seq[2] = [0,1,0,0]
    Seq[3] = [0,1,1,0]
    Seq[4] = [0,0,1,0]
    Seq[5] = [0,0,1,1]
    Seq[6] = [0,0,0,1]
    Seq[7] = [1,0,0,1]
     
    # GPIO.setup(enable_pin, GPIO.OUT)
    GPIO.setup(coil_A_1_pin, GPIO.OUT)
    GPIO.setup(coil_A_2_pin, GPIO.OUT)
    GPIO.setup(coil_B_1_pin, GPIO.OUT)
    GPIO.setup(coil_B_2_pin, GPIO.OUT)
     
    # GPIO.output(enable_pin, 1)
     
    def setStep(w1, w2, w3, w4):
        GPIO.output(coil_A_1_pin, w1)
        GPIO.output(coil_A_2_pin, w2)
        GPIO.output(coil_B_1_pin, w3)
        GPIO.output(coil_B_2_pin, w4)
     
    def forward(delay, steps):
        for i in range(steps):
            for j in range(StepCount):
                setStep(Seq[j][0], Seq[j][1], Seq[j][2], Seq[j][3])
                time.sleep(delay)
     
    def backwards(delay, steps):
        for i in range(steps):
            for j in reversed(range(StepCount)):
                setStep(Seq[j][0], Seq[j][1], Seq[j][2], Seq[j][3])
                time.sleep(delay)

   
    delay = 1
    #steps = 512
    #steps = 262
    steps = 64
    forward(int(delay) / 1000.0, int(steps))
    time.sleep(5)
    steps = 262
    forward(int(delay) / 1000.0, int(steps))
    time.sleep(5)
    steps = 128
    forward(int(delay) / 1000.0, int(steps))
    time.sleep(5)

if __name__ == '__main__':
    # while True:
    #     # delay = raw_input("Time Delay (ms)?")
    #     delay = 4
    #     steps = raw_input("How many steps forward? ")
    #     forward(int(delay) / 1000.0, int(steps))
    #     steps = raw_input("How many steps backwards? ")
    #     backwards(int(delay) / 1000.0, int(steps))
    #data = urllib2.download("http://chrisdesoto.me:7070/schedule/1"
    #print("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~")
    #print(data.read())
    # steps = raw_input("How many steps backwards? ")
    # backwards(int(delay) / 1000.0, int(steps))
    main()
