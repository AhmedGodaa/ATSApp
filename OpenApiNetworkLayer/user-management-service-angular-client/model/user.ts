/**
 * OpenAPI definition
 * No description provided (generated by Openapi Generator https://github.com/openapitools/openapi-generator)
 *
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


export interface User { 
    id?: number;
    email?: string;
    fullName?: string;
    authorities?: Array<string>;
    password?: string;
    nationID?: string;
    address?: string;
    city?: string;
    phone?: string;
    createdDate?: string;
    lastModifiedDate?: string;
    phoneVerified?: boolean;
    emailVerified?: boolean;
}

