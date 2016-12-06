package org.martin.getfreaky.dataObjects;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.martin.getfreaky.dataObjects.Exercise;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-11-01T12:11:37")
@StaticMetamodel(Workout.class)
public class Workout_ { 

    public static volatile ListAttribute<Workout, Exercise> exercises;
    public static volatile SingularAttribute<Workout, String> name;
    public static volatile SingularAttribute<Workout, String> id;

}