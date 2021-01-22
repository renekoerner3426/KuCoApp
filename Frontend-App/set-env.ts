import { writeFile } from 'fs';// Configure Angular `environment.ts` file path
const targetPath = './src/environments/environment.ts';// Load node modules
require('dotenv').load();// `environment.ts` file structure

const envConfigFile = `export const environment = {
   import-service-cluster-ip: '${process.env.API_BASE_URL}',
};

