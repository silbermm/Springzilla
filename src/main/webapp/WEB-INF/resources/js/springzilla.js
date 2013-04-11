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
    self.restoreCaption = 'Select an Image...';  
    
    self.restoreButtonText = "Start Session";
    self.restoreButtonActive = "<i class='icon-refresh icon-spin'></i> Starting";

    self.images = ko.observableArray();
    self.restoreButton = ko.observable();
    self.restoreButton(self.restoreButtonText);
    self.restoreForm = {
        image : ko.observable(),
	imageProtocol : ko.observable(),
	imageType : ko.observable(),
        numberOfClients : ko.observable(),
	maxWaitTime : ko.observable() 		
    };

    self.restoreForm.imageType("restoreDisk");
    self.restoreForm.imageProtocol("multicast");

    self.isRestoreStarting = ko.computed(function() {
        return (self.restoreButton() == self.restoreButtonText);
    }, this); 

    self.submitRestoreForm = function() {
      console.log("in the restore form function");
      self.restoreButton(self.restoreButtonActive);
      var restoreUrl = $("#restoreUrl").val(); 
      $.ajax({
            type: "POST",
            url: restoreUrl,
            data: ko.toJSON(self.restoreForm),
            contentType: "application/json",
            dataType: "json"
        }).fail(function(jqXHR, textStatus) {
            self.error(new Error("Unable to start the restore session: " + textStatus));
        }).always(function() {
            self.restoreButton(self.restoreButtonText);
        }).done(function(data) {
            alert("Dont posting data");	
	}) 
    }

    self.loadImagesData = function() {
	self.images.removeAll();
        var imagesUrl = $("#getImagesUrl").val();
        $.getJSON(imagesUrl).done(function(data) {
            $.each(data, function(i, item) {
                console.log("adding " + item + " to array");
                self.images.push(item);
            });
        }).fail(function(jqxhr, textStatus, error) {
            self.error(new Error("ERROR", "Unable to get the Image locations, is your image location set?"));
        });
    };

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

var springModel = new SpringzillaViewModel();
ko.applyBindings(springModel);
$("#restoreForm").validate({
	rules: {
    	  restoreOptions: {
            required: true,
    	  },
    	  imageName: {
            required: true,
    	  },
    	  restoreProtocol: "required",
	  numberOfClients: {
	    required: true,
	    number: true,
          },
	  maxTimeToWait: {
	    required: true,
	    number: true,
	  }, 

	},
	messages: {
    	  restoreProtocol: "You must choose a network protocol",
          imageName: {
            required: "You must choose an image to restore from",
    	  },
    	  restoreOptions: {
            required: "You must choose to image a partition or disk.",
          },
	  numberOfClients: {
	    required: "Enter the number of computers",
	    number: "A number is required"
	  }, 
	  maxTimeToWait: {
	    required: "Enter a time to wait",
	    number: "A number is required",
	  },
	},
	submitHandler: function(form) {
   	  springModel.submitRestoreForm
        },
	highlight: function (element, errorClass, validClass) {
          $(element).closest('.control-group').removeClass('success').addClass('error');
        },
        unhighlight: function (element, errorClass, validClass) {
          $(element).closest('.control-group').removeClass('error').addClass('success');
        },
	success: function (label) {
          $(label).closest('form').find('.valid').removeClass("invalid");
        },
        errorPlacement: function (error, element) {
          element.closest('.control-group').find('.help-block').html(error.text());
        }
      }); 

