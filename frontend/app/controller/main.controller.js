app.controller('MainController', ['$scope', '$timeout', 'supplierService', function ($scope, $timeout, supplierService) {

    var self = this;
    self.displayForm = false;


    // CRUD OPERATIONS
    self.createSupplier = function (form) {
        form.$submitted = true;
        if (!form.$valid) { return; }
        supplierService.createSupplier(self.tempSupplier)
            .then(function successCallback(response) {
                swal("Fornecedor cadastrado com sucesso!", "", "success");
                executeAfterHttpRequest();
                cleanFormMessages(form);
            }).catch(function errorCallback(error) {
                exceptionHandler(error);
            });
    }

    self.getAllSuppliers = function () {
        supplierService.getPageSuppliers(self.itemsPerPage, self.currentPage)
            .then(function (response) {
                self.suppliers = response.data.data;
                self.totalItems = response.data.totalItems;
                self.totalPages = Math.ceil(self.totalItems / self.itemsPerPage);
            }, function () {
                swal("Não foi possível coletar os dados dos fornecedores, verifique sua conexão!", "", "warning");
            })
    };

    self.updateSupplier = function () {
        supplierService.updateSupplier(self.tempSupplier, self.tempSupplier.id).then(function (response) {
            swal("Fornecedor atualizado com sucesso!", "", "success");
            executeAfterHttpRequest();
        }).catch(function errorCallback(error) {
            exceptionHandler(error);
        });
    }

    self.deleteSupplier = function (id_fornecedor) {
        supplierService.deleteSupplier(id_fornecedor).then(function (response) {
            swal("Exclusão realizada com sucesso!", "", "success");
            executeAfterHttpRequest();
        })
    }

    // User Interface Interaction
    self.openUpdateForm = function (supplier) {
        self.openForm()
        self.tempSupplier = angular.copy(supplier);
        self.isSupplierToBeUpdated = true;
    }

    self.submitUpdateForm = function (form) {
        form.$submitted = true;
        if (!form.$valid) { return; }
        self.updateSupplier();
        cleanFormMessages(form);
        cleanData();
        self.isSupplierToBeUpdated = false;
    }
        
    self.openForm = function () {
        self.displayForm = true;
    }

    self.closeForm = function (form) {
        cleanFormMessages(form);
        cleanData();
        self.isSupplierToBeUpdated = false;
        self.displayForm = false;
    }

   

    // Utility Functions
    function cleanData() {
        self.tempSupplier = {};
    }

    function cleanFormMessages(form) {
        form.$submitted = false;
        form.$setUntouched();
    }



    function changepdateStatus() {
        self.isSupplierToBeUpdated = !self.isSupplierToBeUpdated ;
    }

    self.enableRegisterTemplate = function () {
        self.tempSupplier = {};
    }


    function executeAfterHttpRequest() {
        cleanData();
        self.getAllSuppliers();
    }

    function exceptionHandler(error) {
        let match = /p0=([^&]+)/.exec(error.message);

        if (match && match[1]) {
            let mensagem = decodeURIComponent(match[1]);
            swal(mensagem, "", "warning");
        }
    }

    function enableCleave() {
        angular.element(document).ready(function () {
            let cleave = new Cleave('.cnpj', {
                delimiters: ['.', '.', '/', '-'],
                blocks: [2, 3, 3, 4, 2],
                numericOnly: true
            });
        });
    }

    /* PAGINACAO */
    // Exemplo de lógica de controle de paginação no controlador

    self.pages = [];
    self.currentPage = 1;
    self.itemsPerPage = 5;
    self.totalItems = 0;
    self.totalPages = 0;



    // Função para carregar uma página específica
    self.loadPage = function (page) {
        // Atualizar a página atual
        self.currentPage = page;

        // Chamar a função existente para obter os fornecedores da página atual
        self.getAllSuppliers();
    };

    // Função para atualizar a lista de páginas
    function updatePages() {
        self.pages = [];
        for (let i = 1; i <= self.totalPages; i++) {
            self.pages.push(i);
        }
    }
    $scope.$watch('mainCtrl.suppliers', function (newValue) {
        if (newValue) {
            // Calcular o número total de páginas com base no total de itens e itens por página
            self.totalPages = Math.ceil(self.totalItems / self.itemsPerPage);

            // Atualizar a lista de páginas
            updatePages();
        }
    });

    /* PAGINACAO */



    $scope.$watch('mainCtrl.displayForm', function (newValue, oldValue) {
        if (newValue !== oldValue) {
            $timeout(function () {
                enableCleave();
            });
        }
    });

    // Initialization

    function init() {
        self.isSupplierToBeUpdated = false;
        self.tempSupplier = {};
        self.getAllSuppliers();
    }
    init();
}]);