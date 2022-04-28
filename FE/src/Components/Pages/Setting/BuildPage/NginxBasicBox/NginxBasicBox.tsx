import React, { useState } from 'react';
import Box from '@mui/material/Box';
import Paper from '@mui/material/Paper';
import Grid from '@mui/material/Grid';
import Typography from '@mui/material/Typography';
import TextField from '@mui/material/TextField';
import Checkbox from '@mui/material/Checkbox';
import AddIcon from '@mui/icons-material/Add';
import Button from '@mui/material/Button';
import { v4 as uuid } from 'uuid';
import { Nginx } from 'Components/MDClass/NginxData/NginxData';
import LocationsData, {
  Locations,
} from 'Components/MDClass/LocationsData/LocationsData';
import Proxypass from '../ProxypassBox/ProxypassBox';

interface NginxProps {
  nginxValue: Nginx;
}

export default function NginxBasicBox({ nginxValue }: NginxProps) {
  const [https, setHttps] = useState(nginxValue.https);
  const [domainURL, setDomainURL] = useState(nginxValue.domainUrl);
  const [locations, setLocations] = useState<Locations[]>(nginxValue.locations);
  const [sslCertificate, setSslCertificate] = useState(
    nginxValue.httpsOption.sslCertificate,
  );
  const [sslCertificateKey, setSslCertificateKey] = useState(
    nginxValue.httpsOption.sslCertificateKey,
  );
  const [sslPath, setSslPath] = useState(nginxValue.httpsOption.sslPath);

  const handleAddClick = () => {
    const tempLoacations = new LocationsData();
    nginxValue.locations.push(tempLoacations);
    setLocations([...nginxValue.locations]);
  };

  const handleDomainChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setDomainURL(event.target.value);
    nginxValue.domainUrl = event.target.value;
  };

  const handleCheckBoxChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setHttps(event.target.checked);
    nginxValue.https = event.target.checked;
    if (!event.target.checked) {
      setSslCertificate('');
      nginxValue.httpsOption.sslCertificate = '';
      setSslCertificateKey('');
      nginxValue.httpsOption.sslCertificateKey = '';
      setSslPath('');
      nginxValue.httpsOption.sslPath = '';
    }
  };

  const handleSslCertificateChange = (
    event: React.ChangeEvent<HTMLInputElement>,
  ) => {
    setSslCertificate(event.target.value);
    nginxValue.httpsOption.sslCertificate = event.target.value;
  };

  const handleSslCertificateKeyChange = (
    event: React.ChangeEvent<HTMLInputElement>,
  ) => {
    setSslCertificateKey(event.target.value);
    nginxValue.httpsOption.sslCertificateKey = event.target.value;
  };

  const handleSslPathChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setSslPath(event.target.value);
    nginxValue.httpsOption.sslPath = event.target.value;
  };

  return (
    <Box>
      <Box position="relative" sx={{ top: 20, left: 10 }}>
        <Paper sx={{ padding: 1, textAlign: 'center', width: 450 }}>
          <Typography variant="h5" component="h2">
            NGINX (front with nginx) setting
          </Typography>
        </Paper>
      </Box>
      <Box>
        <Paper sx={{ padding: 3 }}>
          <Grid container spacing={2}>
            <Grid item xs={2} sx={{ margin: 'auto auto' }}>
              <Typography align="center">Domain URL</Typography>
            </Grid>
            <Grid item xs={10}>
              <TextField
                fullWidth
                id="outlined-basic"
                label="Project Name"
                variant="outlined"
                size="small"
                sx={{ my: 1 }}
                placeholder="ProjectName"
                defaultValue={domainURL}
                onChange={handleDomainChange}
              />
            </Grid>
            <Grid item xs={12}>
              {locations.map((value, index) => {
                return (
                  <Proxypass
                    key={uuid()}
                    value={value}
                    locationData={nginxValue.locations[index]}
                  />
                );
              })}
            </Grid>

            <Grid item xs={2} sx={{ margin: 'auto auto' }}>
              <Typography align="center">HTTPS</Typography>
            </Grid>
            <Grid item xs={1}>
              <Checkbox checked={https} onChange={handleCheckBoxChange} />
            </Grid>
            <Grid item xs={9}>
              <Box sx={{ display: 'flex', justifyContent: 'end' }}>
                <Button
                  onClick={handleAddClick}
                  variant="outlined"
                  startIcon={<AddIcon />}
                >
                  Proxypass Add
                </Button>
              </Box>
            </Grid>
            <Grid item xs={1} />
            <Grid item xs={2} sx={{ margin: 'auto auto' }}>
              <Typography align="center">SSL Certificate</Typography>
            </Grid>
            <Grid item xs={9}>
              <TextField
                fullWidth
                disabled={!https}
                id="outlined-basic"
                label="SSL Certificate"
                variant="outlined"
                size="small"
                placeholder="SSL Certificate"
                defaultValue={sslCertificate}
                onChange={handleSslCertificateChange}
              />
            </Grid>
            <Grid item xs={1} />
            <Grid item xs={2} sx={{ margin: 'auto auto' }}>
              <Typography align="center">SSL Certificate Key</Typography>
            </Grid>
            <Grid item xs={9}>
              <TextField
                fullWidth
                disabled={!https}
                id="outlined-basic"
                label="SSL Certificate Key"
                variant="outlined"
                size="small"
                placeholder="SSL Certificate Key"
                defaultValue={sslCertificateKey}
                onChange={handleSslCertificateKeyChange}
              />
            </Grid>

            <Grid item xs={1} />
            <Grid item xs={2} sx={{ margin: 'auto auto' }}>
              <Typography align="center">SSL Path</Typography>
            </Grid>
            <Grid item xs={9}>
              <TextField
                fullWidth
                disabled={!https}
                id="outlined-basic"
                label="SSL Path"
                variant="outlined"
                size="small"
                placeholder="SSL Path"
                defaultValue={sslPath}
                onChange={handleSslPathChange}
              />
            </Grid>
          </Grid>
        </Paper>
      </Box>
    </Box>
  );
}
