FROM node:lts-alpine

# install simple http server for serving static content
RUN npm install -g serve

# make the 'frontend' folder the current working directory
WORKDIR /frontend

# copy both 'package.json' and 'package-lock.json' (if available)
COPY package*.json ./

# install project dependencies
RUN npm install

# copy project files and folders to the current working directory (i.e. 'frontend' folder)
COPY . .

# build app for production with minification
RUN npm run build

EXPOSE 8088
CMD [ "serve","-s", "dist", "-l", "8088" ]
