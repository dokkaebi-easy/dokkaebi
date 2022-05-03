import React, { useState } from 'react';
import Grid from '@mui/material/Grid';
import Typography from '@mui/material/Typography';
import TextField from '@mui/material/TextField';
import DeleteIcon from '@mui/icons-material/Delete';
import Button from '@mui/material/Button';
import { Locations } from 'Components/MDClass/NginxData/NginxData';

interface ProxyProps {
  value: Locations;
  locationData: Locations;
  index: number;
  DelClick: (index: number) => void;
}

export default function ProxypassBox({
  value,
  locationData,
  index,
  DelClick,
}: ProxyProps) {
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

  const handleProxyDelClick = () => {
    DelClick(index);
  };

  return (
    <Grid container spacing={2}>
      <Grid item xs={11}>
        <Typography>Proxy pass URL</Typography>
        <TextField
          fullWidth
          label="ex) http://localhost:8080"
          variant="outlined"
          size="small"
          placeholder="Proxy pass URL"
          defaultValue={url}
          onChange={handleUrlChange}
        />

        <Typography mt={2}>Proxy pass Path</Typography>
        <TextField
          fullWidth
          label="ex) /api"
          variant="outlined"
          size="small"
          placeholder="Proxy pass Path"
          defaultValue={path}
          onChange={handlePathChange}
        />
      </Grid>
      <Grid item sx={{ marginBottom: 0, marginTop: 'auto' }}>
        <Button
          variant="outlined"
          startIcon={<DeleteIcon />}
          size="small"
          onClick={handleProxyDelClick}
          sx={{
            color: 'red',
            borderColor: 'red',
          }}
        >
          Delete
        </Button>
      </Grid>
    </Grid>
  );
}
