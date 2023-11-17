package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcontroller.external.samples.RobotHardware;

import java.lang.Math;
@TeleOp
public class TeleOp1 extends LinearOpMode{
    @Override
    public void runOpMode(){
        RobotHardware map = new RobotHardware(hardwareMap);
        waitForStart();
        double servoposition=0.0;
        double placement_angle=0.80;
        map.servo2.setPosition(0.0);
        map.servo1.setPosition(0.0);
        //initialize servo position
        while(opModeIsActive()){
            double max;
            boolean turn1=false;//whether it is turning
            // POV Mode uses left joystick to go forward & strafe, and right joystick to rotate.
            double lx1=gamepad1.left_stick_x;
            double ly1=gamepad1.left_stick_y;
            double rx1=gamepad1.right_stick_x;
            if((lx1<=0.1) && (lx1>=-0.1)) lx1=0.0;
            if((ly1<=0.1) && (ly1>=-0.1)) ly1=0.0;
            if((rx1<=0.1) && (rx1>=-0.1)) rx1=0.0;
            double axial   = -ly1;  // Note: pushing stick forward gives negative value
            double lateral =  lx1;
            double yaw     =  -rx1;
            if(yaw!=0) turn1=true;
            double power1  =  0;//power1 is the positive power for the slide
            double motorpower=0.8;//motorpower is the default power for all the motors except the driving motors
            if(gamepad1.dpad_up) power1=motorpower;
            double power2=0;//power2 is the negative power for the slide
            if(gamepad1.dpad_down) power2=motorpower;
            double power3=0;//power3 is the power for the intake
            if(gamepad1.left_bumper) power3=motorpower;
            double power4=0;
            if(gamepad1.right_bumper) power4=motorpower;
            if(gamepad1.a)  {servoposition=placement_angle;}
            if(gamepad1.b)  servoposition=0.0;
            // Combine the joystick requests for each axis-motion to determine each wheel's power.
            // Set up a variable for each drive wheel to save the power level for telemetry.
            double leftFrontPower  = axial + lateral + yaw;
            double rightFrontPower = axial - lateral - yaw;
            double leftBackPower   = axial - lateral + yaw;
            double rightBackPower  = axial + lateral - yaw;
            double Power=axial+lateral+yaw;//driving template no need to change
            // Normalize the values so no wheel power exceeds 100%
            // This ensures that the robot maintains the desired motion.
            max=Math.abs(leftFrontPower);
            max = Math.max(Math.abs(leftFrontPower), Math.abs(rightFrontPower));
            max = Math.max(max, Math.abs(leftBackPower));
            max = Math.max(max, Math.abs(rightBackPower));
            if (max > 1.0) {
                leftFrontPower  /= max;
                rightFrontPower /= max;
                leftBackPower   /= max;
                rightBackPower  /= max;
            }//driving template no need to change
            // This is test code:
            //
            // Uncomment the following code to test your motor directions.
            // Each button should make the corresponding motor run FORWARD.
            //   1) First get all the motors to take to correct positions on the robot
            //      by adjusting your Robot Configuration if necessary.
            //   2) Then make sure they run in the correct direction by modifying the
            //      the setDirection() calls above.
            // Once the correct motors move in the correct direction re-comment this code.
            // Send calculated power to wheels


            //Don't change it. Exponential Growth of motor power
            double lfpower=-leftFrontPower*0.9;
            lfpower=Math.pow(lfpower,3);
            if((lfpower<0.1) && (lfpower>-0.1)) lfpower=0;
            double rfpower=-rightFrontPower*0.9;
            rfpower=Math.pow(rfpower,3);
            if((rfpower<0.1) && (rfpower>-0.1)) rfpower=0;
            double lbpower=-leftBackPower*0.9;
            lbpower=Math.pow(lbpower,3);
            if((lbpower<0.1) && (lbpower>-0.1)) lbpower=0;
            double rbpower=rightBackPower*0.9;
            rbpower=Math.pow(rbpower,3);
            if((rbpower<0.1) && (rbpower>-0.1)) rbpower=0;

            if(turn1) {
                lfpower = lfpower * 0.6;
                rfpower = rfpower * 0.6;
                lbpower = lbpower * 0.6;
                rbpower = rbpower * 0.6;
            }
            map.leftFront.setPower(lfpower);
            map.rightFront.setPower(rfpower);
            map.leftBack.setPower(lbpower);
            map.rightBack.setPower(rbpower);
            map.slide1.setPower(power1-power2);// the power on the positive side - power on the negative side = the power
            map.slide2.setPower(power1-power2);
            map.intake1.setPower(power3-power4);
            //set the power for all the powers
            map.servo1.setPosition(servoposition);
            map.servo2.setPosition(servoposition);
            //set position for the servo
            // Show the elapsed game time and wheel power.
            telemetry.addData("Front left/Right", "%4.2f", leftFrontPower, rightFrontPower);
            telemetry.addData("Back  left/Right", "%4.2f, %4.2f", leftBackPower, rightBackPower);
            telemetry.addData("servo","%4.2f",servoposition);
            telemetry.update();
        }
    }
}
