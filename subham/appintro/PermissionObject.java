package com.github.florent37.materialviewpager.subham.appintro;

public class PermissionObject {

    String[] permission;
    int position;

    public PermissionObject(String[] permission, int position){
        this.permission = permission;
        this.position = position;
    }

    public String[] getPermission(){
        return permission;
    }

    public int getPosition(){
        return position;
    }
}
