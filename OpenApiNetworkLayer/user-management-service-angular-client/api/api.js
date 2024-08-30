"use strict";
var __createBinding = (this && this.__createBinding) || (Object.create ? (function(o, m, k, k2) {
    if (k2 === undefined) k2 = k;
    var desc = Object.getOwnPropertyDescriptor(m, k);
    if (!desc || ("get" in desc ? !m.__esModule : desc.writable || desc.configurable)) {
      desc = { enumerable: true, get: function() { return m[k]; } };
    }
    Object.defineProperty(o, k2, desc);
}) : (function(o, m, k, k2) {
    if (k2 === undefined) k2 = k;
    o[k2] = m[k];
}));
var __exportStar = (this && this.__exportStar) || function(m, exports) {
    for (var p in m) if (p !== "default" && !Object.prototype.hasOwnProperty.call(exports, p)) __createBinding(exports, m, p);
};
Object.defineProperty(exports, "__esModule", { value: true });
exports.APIS = void 0;
__exportStar(require("./adminController.service"), exports);
const adminController_service_1 = require("./adminController.service");
__exportStar(require("./authenticationController.service"), exports);
const authenticationController_service_1 = require("./authenticationController.service");
__exportStar(require("./emailController.service"), exports);
const emailController_service_1 = require("./emailController.service");
__exportStar(require("./emailVerificationController.service"), exports);
const emailVerificationController_service_1 = require("./emailVerificationController.service");
__exportStar(require("./passwordResetController.service"), exports);
const passwordResetController_service_1 = require("./passwordResetController.service");
__exportStar(require("./userController.service"), exports);
const userController_service_1 = require("./userController.service");
__exportStar(require("./workspaceController.service"), exports);
const workspaceController_service_1 = require("./workspaceController.service");
__exportStar(require("./workspacesController.service"), exports);
const workspacesController_service_1 = require("./workspacesController.service");
exports.APIS = [adminController_service_1.AdminControllerService, authenticationController_service_1.AuthenticationControllerService, emailController_service_1.EmailControllerService, emailVerificationController_service_1.EmailVerificationControllerService, passwordResetController_service_1.PasswordResetControllerService, userController_service_1.UserControllerService, workspaceController_service_1.WorkspaceControllerService, workspacesController_service_1.WorkspacesControllerService];
