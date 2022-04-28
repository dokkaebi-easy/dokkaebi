import React, { useState } from 'react';
import Grid from '@mui/material/Grid';
import Typography from '@mui/material/Typography';
import TextField from '@mui/material/TextField';
import Box from '@mui/material/Box';
import LocationsData, {
  Locations,
} from 'Components/MDClass/LocationsData/LocationsData';

interface ProxyProps {
  value: Locations;
  locationData: Locations;
}

export default function ProxypassBox({ value, locationData }: ProxyProps) {
  const [path, setPath] = useState(value.location);
  const [url, setUrl] = useState(value.url);

  const handlePathChange = (event: any) => {
    setPath(event.target.value);
    locationData.location = event.target.value;
  };

  const handleUrlChange = (event: any) => {
    setUrl(event.target.value);
    locationData.url = event.target.value;
  };
  return (
    <Box sx={{ padding: 3 }}>
      <Grid container spacing={2}>
        <Grid item xs={2} sx={{ margin: 'auto auto' }}>
          <Typography align="center">Proxy pass Path</Typography>
        </Grid>
        <Grid item xs={10}>
          <TextField
            fullWidth
            label="Proxy pass Path"
            variant="outlined"
            size="small"
            placeholder="Proxy pass Path"
            defaultValue={path}
            onChange={handlePathChange}
          />
        </Grid>
        <Grid item xs={2} sx={{ margin: 'auto auto' }}>
          <Typography align="center">Proxy pass URL</Typography>
        </Grid>
        <Grid item xs={10}>
          <TextField
            fullWidth
            label="Proxy pass URL"
            variant="outlined"
            size="small"
            placeholder="Proxy pass URL"
            defaultValue={url}
            onChange={handleUrlChange}
          />
        </Grid>
      </Grid>
    </Box>
  );
}
