function SpringzillaViewModel() {

    var self = this;
    var saveText = "Save Changes";

    self.error = ko.observable();
    self.dismissError = function() {
        self.error(null);
    };


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


    /***********************************
     **** The Settings Page Form(s) **** 
     **********************************/
    self.locationSaveText = ko.observable();
    self.locationSaveText(saveText);
    self.multicastSaveText = ko.observable();
    self.multicastSaveText(saveText);
    self.settingsError = ko.observable();
    self.dismissSettingsError = function() {
        self.settingsError(null);
    };

    // Multicast Settings
    self.mcastSettings = {
        multicastPort: ko.observable(),
        multicastTTL: ko.observable(),
        rdvAddress: ko.observable(),
        senderAddress: ko.observable()
    };

    // General Settings
    self.generalSettings = {
        imageLocation: ko.observable(),
    }

    self.isMulticastSaving = ko.computed(function() {
        return (self.multicastSaveText() == saveText);
    }, this);

    self.isLocationSaving = ko.computed(function() {
        return (self.locationSaveText() == saveText);
    }, this);

    self.multicastSave = function() {
        var multicastUrl = $("#getMulticastUrl").val();
        self.multicastSaveText("<i class='icon-refresh icon-spin'></i> Saving");
        console.log(ko.toJSON(self.mcastSettings));
        $.ajax({
            type: "PUT",
            url: multicastUrl,
            data: ko.toJSON(self.mcastSettings),
            contentType: "application/json",
            dataType: "json"
        }).done(function(msg) {

        }).fail(function(jqXHR, textStatus) {
            self.error(new Error("Unable to save multicast settings: " + textStatus));
        }).always(function() {
            self.multicastSaveText(saveText);
        });

    };

    self.locationSave = function() {
        var generalUrl = $("#getGeneralUrl").val();
        self.locationSaveText("<i class='icon-refresh icon-spin'></i> Saving");
        $.ajax({
            type: "PUT",
            url: generalUrl,
            data: ko.toJSON(self.generalSettings),
            contentType: "application/json",
            dataType: "json"
        }).fail(function(jqXHR, textStatus) {
            self.error(new Error("Unable to save general settings: " + textStatus));
            self.loadGeneralSettingsData();
        }).always(function() {
            self.locationSaveText(saveText);
        });
    };

    self.loadMcastSettingsData = function() {
        var multicastUrl = $("#getMulticastUrl").val();

        // Get Multicast Settings
        $.getJSON(multicastUrl).done(function(data) {
            self.mcastSettings.multicastPort(data.multicastPort);
            self.mcastSettings.multicastTTL(data.multicastTTL);
            self.mcastSettings.rdvAddress(data.rdvAddress);
            self.mcastSettings.senderAddress(data.senderAddress);
        }).fail(function(jqxhr, textStatus, error) {
            var err = textStatus + ', ' + error;
            console.log("Request Failed: " + err);
            self.error(new Error("ERROR", "Unable to get Multicast Settings: " + error));
        });
    };

    self.loadGeneralSettingsData = function() {
        var generalUrl = $("#getGeneralUrl").val();
        // Get General Settings
        $.getJSON(generalUrl).done(function(data) {
            console.log(data);
            $.each(data, function(i, item) {
                if (item.settingName == "imageLocation") {
                    self.generalSettings.imageLocation(item.settingValue);
                }
            });
        }).fail(function(jqxhr, textStatus, error) {
            console.log("error: " + error + " " + jqxhr);
            self.error(new Error("ERROR", "Unable to get General Settings: " + error));
        });
    };
    /****** END SETTINGS PAGE ********/


    /********************************/
    /******* CLONING PAGE ***********/
    /********************************/
    self.imageList = ko.observableArray();


    self.loadImagesData = function() {
        var imagesUrl = $("#getImagesUrl").val();
        $.getJSON(imagesUrl).done(function(data) {
            console.log(data);
            $.each(data, function(i, item) {
                console.log("adding " + item + " to array");
                self.imageList.push(item);
            });
        }).fail(function(jqxhr, textStatus, error) {
            console.log("error: " + error + " " + jqxhr);
            self.error(new Error("ERROR", "Unable to get the Image locations, is your image location set?"));
        });
    };

    self.stopDownloadSession = function() {

    }

    self.startDownloadSession = function() {

    }


    /***** END CLONING PAGE *********/


    // Client-side routes    
    Sammy(function() {
        this.get('#:nav', function() {
            self.chosenNavId(this.params.nav);
            // also load the data for each page when clicked (initially)
            switch (this.params.nav) {
                case "Settings":
                    self.loadMcastSettingsData();
                    self.loadGeneralSettingsData();
                    break;
                case "Cloning":
                    self.loadImagesData();
                    break;
                default:
                    // do nothing
                    break;
            }
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
