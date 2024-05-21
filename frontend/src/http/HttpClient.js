import axios from "axios";
import {URL} from "../config/app-url.js";

/**
 *
 * @type {axios.AxiosInstance}
 */
const HttpClient  = axios.create({
    baseURL: URL.backendApi,
});

export default HttpClient;
