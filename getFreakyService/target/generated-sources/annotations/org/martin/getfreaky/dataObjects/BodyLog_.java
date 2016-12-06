package org.martin.getfreaky.dataObjects;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.martin.getfreaky.dataObjects.Measurements;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-11-01T12:11:37")
@StaticMetamodel(BodyLog.class)
public class BodyLog_ { 

    public static volatile SingularAttribute<BodyLog, Integer> bodyFatPercentage;
    public static volatile SingularAttribute<BodyLog, Float> weight;
    public static volatile SingularAttribute<BodyLog, Long> id;
    public static volatile SingularAttribute<BodyLog, Measurements> measurements;

}