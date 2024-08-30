"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.CustomHttpParameterCodec = void 0;
/**
 * Custom HttpParameterCodec
 * Workaround for https://github.com/angular/angular/issues/18261
 */
class CustomHttpParameterCodec {
    encodeKey(k) {
        return encodeURIComponent(k);
    }
    encodeValue(v) {
        return encodeURIComponent(v);
    }
    decodeKey(k) {
        return decodeURIComponent(k);
    }
    decodeValue(v) {
        return decodeURIComponent(v);
    }
}
exports.CustomHttpParameterCodec = CustomHttpParameterCodec;
