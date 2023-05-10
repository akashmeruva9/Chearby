package com.akashmeruva.chearby.encrypted

class Chat {

    var text : String? = null;
    var time : String? = null
    var date : String? = null;
    var status : String? = null;
    var type : String? = null;

    constructor()
    constructor(text : String , time : String, date :String , status : String , type : String )
    {
        this.text = text
        this.time = time
        this.date = date
        this.status = status
        this.type = type
    }
}