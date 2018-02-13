package torggler.utils.converters;

import torggler.modelFx.UserFx;
import torggler.tables.TabUsers;

public class ConverterUser {

    public static TabUsers convertToUsers(UserFx userFx) {
        TabUsers tabUsers = new TabUsers ( );
        tabUsers.setId_user (userFx.getId ( ));
        tabUsers.setLogin (userFx.getLogin ( ));
        tabUsers.setPassword (userFx.getPassword ( ));
        tabUsers.setName (userFx.getName ( ));
        tabUsers.setSurname (userFx.getSurname ( ));
        tabUsers.setDepartment (userFx.getDepartment ( ));
        //  tabUsers.setCreate_date (ConverterDate.convertToDate (userFx.getCreate_date ( )));
        return tabUsers;
    }

    public static UserFx convertToUserFx(TabUsers tabUsers) {
        UserFx userFx = new UserFx ( );
        userFx.setId (tabUsers.getId_user ( ));
        userFx.setLogin (tabUsers.getLogin ( ));
        userFx.setPassword (tabUsers.getPassword ( ));
        userFx.setName (tabUsers.getName ( ));
        userFx.setSurname (tabUsers.getSurname ( ));
        userFx.setDepartment (tabUsers.getDepartment ( ));
        // userFx.setCreate_date (ConverterDate.convertToLocalDate (tabUsers.getCreate_date ( )));
        return userFx;
    }

}