package org.martin.getfreaky.dataObjects;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.martin.getfreaky.dataObjects.WorkingSet;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-11-01T12:11:36")
@StaticMetamodel(Exercise.class)
public class Exercise_ { 

    public static volatile SingularAttribute<Exercise, String> exerciseId;
    public static volatile ListAttribute<Exercise, WorkingSet> sets;
    public static volatile SingularAttribute<Exercise, String> name;

}