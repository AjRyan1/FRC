/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

//import edu.wpi.first.wpilibj.SimpleRobot;
import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.image.BinaryImage;
import edu.wpi.first.wpilibj.image.CriteriaCollection;
import edu.wpi.first.wpilibj.image.LinearAverages;
import edu.wpi.first.wpilibj.image.NIVision;
import edu.wpi.first.wpilibj.image.NIVisionException;
import edu.wpi.first.wpilibj.image.ParticleAnalysisReport;

/**
 *
 * @author mitch
 */
public class VisionAnalysis {
    

    final int XMAXSIZE = 24;
    final int XMINSIZE = 24;
    final int YMAXSIZE = 24;
    final int YMINSIZE = 48;
    final double xMax[] = {1, 1, 1, 1, .5, .5, .5, .5, .5, .5, .5, .5, .5, .5, .5, .5, .5, .5, .5, .5, 1, 1, 1, 1};
    final double xMin[] = {.4, .6, .1, .1, .1, .1, .1, .1, .1, .1, .1, .1, .1, .1, .1, .1, .1, .1, .1, .1, .1, .1, 0.6, 0};
    final double yMax[] = {1, 1, 1, 1, .5, .5, .5, .5, .5, .5, .5, .5, .5, .5, .5, .5, .5, .5, .5, .5, 1, 1, 1, 1};
    final double yMin[] = {.4, .6, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05,
								.05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05,
								.05, .05, .6, 0};
    
    final int RECTANGULARITY_LIMIT = 60;
    final int ASPECT_RATIO_LIMIT = 75;
    final int X_EDGE_LIMIT = 40;
    final int Y_EDGE_LIMIT = 60;
    
    final int X_IMAGE_RES = 640;          //X Image resolution in pixels, should be 160, 320 or 640
   // final double VIEW_ANGLE = 43.5;       //Axis 206 camera
  final double VIEW_ANGLE = 48;       //Axis M1011 camera
    
    AxisCamera camera;          // the axis camera object (connected to the switch)
    CriteriaCollection cc;      // the criteria for doing the particle filter operation
    
   
    
    
    
  double computeDistance (BinaryImage image, ParticleAnalysisReport report, int particleNumber, boolean outer) throws NIVisionException {
            double rectShort, height;
            int targetHeight;

            rectShort = NIVision.MeasureParticle(image.image, particleNumber, false, NIVision.MeasurementType.IMAQ_MT_EQUIVALENT_RECT_SHORT_SIDE);
            //using the smaller of the estimated rectangle short side and the bounding rectangle height results in better performance
            //on skewed rectangles
            height = Math.min(report.boundingRectHeight, rectShort);
            targetHeight = outer ? 29 : 21;

            return X_IMAGE_RES * targetHeight / (height * 12 * 2 * Math.tan(VIEW_ANGLE*Math.PI/(180*2)));
    }
    public double scoreAspectRatio(BinaryImage image, ParticleAnalysisReport report, int particleNumber, boolean outer) throws NIVisionException
    {
        double rectLong, rectShort, aspectRatio, idealAspectRatio;

        rectLong = NIVision.MeasureParticle(image.image, particleNumber, false, NIVision.MeasurementType.IMAQ_MT_EQUIVALENT_RECT_LONG_SIDE);
        rectShort = NIVision.MeasureParticle(image.image, particleNumber, false, NIVision.MeasurementType.IMAQ_MT_EQUIVALENT_RECT_SHORT_SIDE);
        idealAspectRatio = outer ? (62/29) : (62/20);	//Dimensions of goal opening + 4 inches on all 4 sides for reflective tape
	
        //Divide width by height to measure aspect ratio
        if(report.boundingRectWidth > report.boundingRectHeight){
            //particle is wider than it is tall, divide long by short
            aspectRatio = 100*(1-Math.abs((1-((rectLong/rectShort)/idealAspectRatio))));
        } else {
            //particle is taller than it is wide, divide short by long
                aspectRatio = 100*(1-Math.abs((1-((rectShort/rectLong)/idealAspectRatio))));
        }
	return (Math.max(0, Math.min(aspectRatio, 100.0)));		//force to be in range 0-100
    }
    
     boolean scoreCompare(Scores scores, boolean outer){
            boolean isTarget = true;

            isTarget &= scores.rectangularity > RECTANGULARITY_LIMIT;
            if(outer){
                    isTarget &= scores.aspectRatioOuter > ASPECT_RATIO_LIMIT;
            } else {
                    isTarget &= scores.aspectRatioInner > ASPECT_RATIO_LIMIT;
            }
            isTarget &= scores.xEdge > X_EDGE_LIMIT;
            isTarget &= scores.yEdge > Y_EDGE_LIMIT;

            return isTarget;
    }
    
     
     double scoreRectangularity(ParticleAnalysisReport report){
            if(report.boundingRectWidth*report.boundingRectHeight !=0){
                    return 100*report.particleArea/(report.boundingRectWidth*report.boundingRectHeight);
            } else {
                    return 0;
            }	
    }
     
     public double scoreXEdge(BinaryImage image, ParticleAnalysisReport report) throws NIVisionException
    {
        double total = 0;
        LinearAverages averages;
        
        NIVision.Rect rect = new NIVision.Rect(report.boundingRectTop, report.boundingRectLeft, report.boundingRectHeight, report.boundingRectWidth);
        averages = NIVision.getLinearAverages(image.image, LinearAverages.LinearAveragesMode.IMAQ_COLUMN_AVERAGES, rect);
        float columnAverages[] = averages.getColumnAverages();
        for(int i=0; i < (columnAverages.length); i++){
                if(xMin[(i*(XMINSIZE-1)/columnAverages.length)] < columnAverages[i] 
                   && columnAverages[i] < xMax[i*(XMAXSIZE-1)/columnAverages.length]){
                        total++;
                }
        }
        total = 100*total/(columnAverages.length);
        return total;
    }
     
     public double scoreYEdge(BinaryImage image, ParticleAnalysisReport report) throws NIVisionException
    {
        double total = 0;
        LinearAverages averages;
        
        NIVision.Rect rect = new NIVision.Rect(report.boundingRectTop, report.boundingRectLeft, report.boundingRectHeight, report.boundingRectWidth);
        averages = NIVision.getLinearAverages(image.image, LinearAverages.LinearAveragesMode.IMAQ_ROW_AVERAGES, rect);
        float rowAverages[] = averages.getRowAverages();
        for(int i=0; i < (rowAverages.length); i++){
                if(yMin[(i*(YMINSIZE-1)/rowAverages.length)] < rowAverages[i] 
                   && rowAverages[i] < yMax[i*(YMAXSIZE-1)/rowAverages.length]){
                        total++;
                }
        }
        total = 100*total/(rowAverages.length);
        return total;
    }
    

       
     public void VisionAnalysis() {
        camera = AxisCamera.getInstance();  // get an instance of the camera
        cc = new CriteriaCollection();      // create the criteria for the particle filter
        cc.addCriteria(NIVision.MeasurementType.IMAQ_MT_AREA, 500, 65535, false);
    }
 
}
