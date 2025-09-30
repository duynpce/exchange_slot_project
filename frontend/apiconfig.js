export const base_url = " ";
// ================ ACCOUNT ENDPOINT ======================
export const register_api = `${base_url}/account/register`;
export const login_api = `${base_url}/account/login`;
export const reset_password_api = `${base_url}/account/reset_password`;
// ================ EXCHANGE CLASS ENDPOINT ===============
export const exchange_class_create_api = `${base_url}/exchangeClass`;
export const exchange_class_delete_api = (id) => `${base_url}/exchangeClass/${id}`;
export const exchange_class_get_by_classCode_api = (classCode) => `${BASE_URL}/exchangeClass/${classCode}`;
