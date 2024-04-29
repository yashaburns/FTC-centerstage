package org.firstinspires.ftc.teamcode.OCV;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.drive.DriveConstants;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.vision.VisionPortal;

@Autonomous
public class RedBACKDROPAuto extends OpMode {
    private OCVVisionProc drawProcessor;
    private VisionPortal visionPortal;
    private Servo pixelDrop = null;
    private Servo backPixelDrop = null;
    int positionDetect = 0;

    @Override
    public void init() {
        drawProcessor = new OCVVisionProc();
        pixelDrop = hardwareMap.get(Servo.class, "pDrop");
        backPixelDrop = hardwareMap.get(Servo.class, "p3Drop");
        visionPortal = VisionPortal.easyCreateWithDefaults(hardwareMap.get(WebcamName.class, "Webcam 1"), drawProcessor);
        visionPortal.resumeStreaming();
    }

    @Override
    public void init_loop() {
        visionPortal.resumeStreaming();
        visionPortal.setProcessorEnabled(drawProcessor, true);

        switch (drawProcessor.getSelection()) {
            case LEFT:
                positionDetect = 1;
                break;
            case MIDDLE:
                positionDetect = 2;
                break;
            case RIGHT:
                positionDetect = 3;
                break;
            case NONE:
                positionDetect = 0;
                break;
        }
    }

    @Override
    public void start() {

        visionPortal.resumeStreaming();
        visionPortal.setProcessorEnabled(drawProcessor, true);
    }

    @Override
    public void loop()  {

        telemetry.addData("Identified", drawProcessor.getSelection());


        switch (positionDetect) {
            case 1:
                visionPortal.setProcessorEnabled(drawProcessor, false);
                visionPortal.stopStreaming();
                SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
                Pose2d startPoseL = new Pose2d(11, -61, Math.toRadians(-90));
                drive.setPoseEstimate(startPoseL);
                TrajectorySequence trajL = drive.trajectorySequenceBuilder(startPoseL)
                        .lineToConstantHeading(new Vector2d(11, -41))
                        .lineToLinearHeading(new Pose2d(10, -36, Math.toRadians(-30)))
                        .lineToConstantHeading(new Vector2d(13, -36))

                        .addDisplacementMarker(16,() -> {
                            pixelDrop.setPosition(0);
                        })

                        .lineToLinearHeading(new Pose2d(40, -23, Math.toRadians(180)),
                                SampleMecanumDrive.getVelocityConstraint(30, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                                SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL)
)

                        .addDisplacementMarker(() -> {
                            pixelDrop.setPosition(1);
                        })

                        .lineToConstantHeading(new Vector2d(50, -24.5))

                        .addDisplacementMarker(() -> {
                            backPixelDrop.setPosition(0.79);
                        })

                        .lineToConstantHeading(new Vector2d(51, -24.5))
                        .lineToConstantHeading(new Vector2d(45, -24.5),
                                SampleMecanumDrive.getVelocityConstraint(30, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                                SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL))

                        .addDisplacementMarker(() -> {
                            backPixelDrop.setPosition(1);
                        })

                        .lineToConstantHeading(new Vector2d(45, -60),
                                SampleMecanumDrive.getVelocityConstraint(30, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                                SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL))
                        .lineToConstantHeading(new Vector2d(60,-60))

                        .build();

                drive.followTrajectorySequence(trajL);

                visionPortal.setProcessorEnabled(drawProcessor, false);
                positionDetect = 0;
                break;
            case 3:
                visionPortal.setProcessorEnabled(drawProcessor, false);
                visionPortal.stopStreaming();
                drive = new SampleMecanumDrive(hardwareMap);

                Pose2d startPoseR = new Pose2d(11, -61, Math.toRadians(-90));

                drive.setPoseEstimate(startPoseR);
                TrajectorySequence trajR = drive.trajectorySequenceBuilder(startPoseR)
                        .strafeTo(new Vector2d(18, -30))

                        .addDisplacementMarker(() -> {
                            pixelDrop.setPosition(0);
                        })

                        .lineToConstantHeading(new Vector2d(18.00, -42.00))
                        .lineToConstantHeading(new Vector2d(26.00, -45.00))

                        .addDisplacementMarker(() -> {
                            pixelDrop.setPosition(1);
                        })

                        .lineToLinearHeading(new Pose2d(38, -40, Math.toRadians(180)))
                        .lineToConstantHeading(new Vector2d(50, -36))

                        .addDisplacementMarker(() -> {
                            backPixelDrop.setPosition(0.78);
                        })

                        .lineToConstantHeading(new Vector2d(51, -36))
                        .lineToConstantHeading(new Vector2d(44, -36),
                                SampleMecanumDrive.getVelocityConstraint(30, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                                SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL))

                        .addDisplacementMarker(() -> {
                            backPixelDrop.setPosition(1);
                        })

                        .lineToConstantHeading(new Vector2d(44, -60),
                                SampleMecanumDrive.getVelocityConstraint(30, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                                SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL))
                        .lineToConstantHeading(new Vector2d(60, -60),
                                SampleMecanumDrive.getVelocityConstraint(30, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                                SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL))
                        .build();
                drive.followTrajectorySequence(trajR);
                visionPortal.setProcessorEnabled(drawProcessor, false);
                positionDetect = 0;
                break;
            case 2:
                visionPortal.setProcessorEnabled(drawProcessor, false);
                visionPortal.stopStreaming();
                drive = new SampleMecanumDrive(hardwareMap);

                Pose2d startPoseM = new Pose2d(11, -61, Math.toRadians(-90));

                // enderman
                drive.setPoseEstimate(startPoseM);
                TrajectorySequence trajM = drive.trajectorySequenceBuilder(startPoseM)
                        .lineToConstantHeading(new Vector2d(12.00, -34))
                        .lineToConstantHeading(new Vector2d(12.00, -38))

                        .addDisplacementMarker(1.8,() -> {
                            pixelDrop.setPosition(0);
                        })

                        .lineToLinearHeading(new Pose2d(42, -32, Math.toRadians(180)))

                        .addDisplacementMarker(() -> {
                            pixelDrop.setPosition(1);
                        })
                        .lineToConstantHeading(new Vector2d(50.0, -30),
                                SampleMecanumDrive.getVelocityConstraint(40, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                                SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL))

                        .addDisplacementMarker(() -> {
                            backPixelDrop.setPosition(0.78);
                        })

                        .lineToConstantHeading(new Vector2d(51.00, -28))
                        .lineToConstantHeading(new Vector2d(49, -28),
                                SampleMecanumDrive.getVelocityConstraint(40, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                                SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL))

                        .addDisplacementMarker(() -> {
                            backPixelDrop.setPosition(1);
                        })

                        .lineToConstantHeading(new Vector2d(45, -30),
                                SampleMecanumDrive.getVelocityConstraint(40, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                                SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL))
                        .lineToConstantHeading(new Vector2d(45, -60),
                                SampleMecanumDrive.getVelocityConstraint(40, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                                SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL))
                        .lineToConstantHeading(new Vector2d(59, -59),
                                SampleMecanumDrive.getVelocityConstraint(40, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                                SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL))
                        .build();
                drive.followTrajectorySequence(trajM);
                visionPortal.setProcessorEnabled(drawProcessor, false);
                positionDetect = 0;
                break;
            case 0:
                break;
        }
    }
}
