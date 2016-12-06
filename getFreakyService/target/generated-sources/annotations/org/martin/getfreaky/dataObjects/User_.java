package org.martin.getfreaky.dataObjects;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.martin.getfreaky.dataObjects.DayLog;
import org.martin.getfreaky.dataObjects.User;
import org.martin.getfreaky.dataObjects.Workout;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-11-01T12:11:37")
@StaticMetamodel(User.class)
public class User_ { 

    public static volatile SingularAttribute<User, String> googleId;
    public static volatile SingularAttribute<User, String> password;
    public static volatile ListAttribute<User, User> friendsOf;
    public static volatile ListAttribute<User, Workout> workouts;
    public static volatile SingularAttribute<User, String> facebookId;
    public static volatile SingularAttribute<User, String> name;
    public static volatile ListAttribute<User, DayLog> dayLogs;
    public static volatile SingularAttribute<User, String> id;
    public static volatile ListAttribute<User, User> friends;
    public static volatile SingularAttribute<User, String> email;

}