from morse.builder import *


robots = []
poses = []
motions = []
sicks = []
for i in range(5):
    robots.append(ATRV("Robot"+str(i)))
    robots[i].translate(x=-2.0*i,y=0.0,z=0.0)

    motions.append(MotionVW("robot"+str(i)+".motion"))
    robots[i].append(motions[i])

    poses.append(Pose("robot"+str(i)+".pose"))
    poses[i].translate(x=0.0,y=0.0,z=0.0)
    poses[i].rotate(x=0.0,y=0.0,z=0.0)
    robots[i].append(poses[i])

    sicks.append(Sick("robot"+str(i)+".sick"))
    sicks[i].translate(x=0.1,y=0,z=0.85)
    sicks[i].properties(Visible_arc=True, laser_range=5.0)
    robots[i].append(sicks[i])

    protocol = "socket"

    robots[i].add_interface(protocol)
    poses[i].add_interface(protocol)
    motions[i].add_interface(protocol)
    sicks[i].add_interface(protocol)

env = Environment('palco.blend')
env.place_camera([5, -5, 6])
env.aim_camera([1.0470, 0, 0.7854])

