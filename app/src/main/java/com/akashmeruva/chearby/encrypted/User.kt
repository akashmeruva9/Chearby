package com.akashmeruva.chearby.encrypted

class User {
     var name : String? = null;
     var phonenumber : String? = null
     var recent_msg : String? = null;
     var count_msg : Int? = null;
     var image_link : String? = null

    constructor()
    constructor(name : String , phonenumber : String, image_link :String , recent_msg : String , count_msg: Int  , email :String)
    {
        this.name = name
        this.phonenumber = phonenumber
        this.count_msg = count_msg
        this.recent_msg = recent_msg
        this.image_link = image_link
    }

}