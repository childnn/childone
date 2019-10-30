package geodesy;

import org.gavaghan.geodesy.Ellipsoid;
import org.gavaghan.geodesy.GeodeticCalculator;
import org.gavaghan.geodesy.GeodeticCurve;
import org.gavaghan.geodesy.GlobalCoordinates;
import org.junit.jupiter.api.Test;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2019/11/16 16:59
 */
public class GeoTest {
    @Test
    public void test() {
        GlobalCoordinates source = new GlobalCoordinates(29.490295, 106.486654);
        GlobalCoordinates target = new GlobalCoordinates(29.615467, 106.581515);

        double meter = getDistanceMeter(source, target, Ellipsoid.Sphere);
        double meter1 = getDistanceMeter(source, target, Ellipsoid.WGS84);

        System.out.println("Sphere坐标系计算结果: " + meter + "米");
        System.out.println("WGS84坐标系计算结果: " + meter1 + "米");
    }

    public static double getDistanceMeter(GlobalCoordinates gpsFrom, GlobalCoordinates gpsTo, Ellipsoid ellipsoid) {
        GeodeticCurve geodeticCurve = new GeodeticCalculator().calculateGeodeticCurve(ellipsoid, gpsFrom, gpsTo);
        return geodeticCurve.getEllipsoidalDistance();
    }
}
