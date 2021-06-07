package com.ntsua.thelife;

public enum NameOfRelationship {
    Friend("Bạn bè"), Mom("Mẹ"), Dad("Bố"), Wife("Vợ"), Husband("Chồng"),
    GirlFriend("Bạn gái"), BoyFriend("Bạn trai");

    private  String quanhe;

    private NameOfRelationship(String quanhe)
    {
        this.quanhe = quanhe;
    }

    @Override
    public String toString() {
        return quanhe;
    }
}
