package org.martin.getfreaky.dataObjects;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.martin.getfreaky.dataObjects.BodyLog;
import org.martin.getfreaky.dataObjects.ProgressPicture;
import org.martin.getfreaky.dataObjects.Workout;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-11-01T12:11:37")
@StaticMetamodel(DayLog.class)
public class DayLog_ { 

    public static volatile SingularAttribute<DayLog, Date> date;
    public static volatile SingularAttribute<DayLog, BodyLog> bodylog;
    public static volatile SingularAttribute<DayLog, ProgressPicture> progressPicture;
    public static volatile SingularAttribute<DayLog, String> dayLogId;
    public static volatile ListAttribute<DayLog, Workout> workoutResults;

}