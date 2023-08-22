import axios from "axios";

const http = axios.create({
  baseUrl: "http://localhost:9000/image-upload",
});

export default {
  uploadImage(image, recipeId) {
    return http.put(`/${recipeId}`, image);
  },
};
