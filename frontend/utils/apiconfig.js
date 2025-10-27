export const base_url = "https://exchangeslot-api-testing.onrender.com";
// ================ ACCOUNT ENDPOINT ======================
export const register_api = `${base_url}/register`;
export const login_api = `${base_url}/login`;
export const reset_password_api = `${base_url}/reset_password`;
export const account_api = `${base_url}/account`;
// ================ EXCHANGE CLASS ENDPOINT ===============
export const exchange_class_create_api = `${base_url}/exchange_class`;
export const exchange_class_delete_api = (id) => `${base_url}/exchange_class/${id}`;
export const exchange_class_get_by_classCode_api = (classCode, page) => `${base_url}/exchange_class/class_code/${classCode}/page/${page}`;
export const exchange_slot_create_api = `${base_url}/exchange_slot`;

export const exchange_class_get_by_slot_api = (slot, page) => `${base_url}/exchange_slot/slot/${slot}/page/${page}`