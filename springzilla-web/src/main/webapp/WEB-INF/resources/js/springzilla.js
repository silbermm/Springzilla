function SpringzillaViewModel() {

    var self = this;
    var saveText = "Save Changes";
    
    self.error = ko.observable();
    self.dismissError = function() {
        self.error(null);
    }
    
    
    /*********************************
     *********** Navigation ********** 
     *********************************/    
    self.navigation = [new NavigationItem("Dashboard", "icon-home"),
        new NavigationItem("Cloning", "icon-sitemap"),
        new NavigationItem("iPxe", "icon-cloud-download"),
        new NavigationItem("Settings", "icon-cogs")
    ];
    self.chosenNavId = ko.observable();
    self.isDashboard = ko.computed(function() {
        return (self.chosenNavId() == "Dashboard");
    }, this);

    self.isCloning = ko.computed(function() {
        return (self.chosenNavId() == "Cloning");
    }, this);

    self.isSettings = ko.computed(function() {
        return (self.chosenNavId() == "Settings");
    }, this);
    
    self.goToNav = function(nav) {
        location.hash = nav.name;
    };
    /********************************/ 
    
    
    
    /*********************************
     **** The Settings Page Forms **** 
     *********************************/
    self.locationSaveText = ko.observable();
    self.locationSaveText(saveText)
    self.multicastSaveText = ko.observable();
    self.multicastSaveText(saveText);   
    
    self.isMulticastSaving = ko.computed(function() {
        return (self.multicastSaveText() == saveText);
    }, this);
    
    self.isLocationSaving = ko.computed(function() {
        return (self.locationSaveText() == saveText);
    }, this);
    
    self.multicastSave = function() {
        self.multicastSaveText("<i class='icon-refresh icon-spin'></i> Saving")        
        alert("Clicked the button");  
        self.multicastSaveText(saveText);
    }
    
    self.locationSave = function() {
        self.locationSaveText("<i class='icon-refresh icon-spin'></i> Saving")
        alert("Do something");
        self.locationSaveText(saveText);
    }        
    /********************************/               
    

    // Client-side routes    
    Sammy(function() {
        this.get('#:nav', function() {
            self.chosenNavId(this.params.nav);
            //self.chosenMailData(null);
            //$.get("/mail", { folder: this.params.folder }, self.chosenFolderData);
        });
        
    }).run();
}



function NavigationItem(name, icon) {
    this.name = name;
    this.icon = icon;
}

function Error(title, message) {
    this.title = title;
    this.message = message;
}

ko.applyBindings(new SpringzillaViewModel());