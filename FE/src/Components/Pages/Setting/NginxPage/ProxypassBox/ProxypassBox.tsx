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
    <Grid container spacing={1}>
      <Grid item xs={2} sx={{ marginY: 'auto' }}>
        <Typography fontFamily="Noto Sans KR" fontSize={20}>
          Proxy pass URL
        </Typography>
      </Grid>
      <Grid item xs={9}>
        <TextField
          fullWidth
          label="Proxy pass URL"
          InputLabelProps={{
            sx: { color: 'rgb(200,200,200)' },
          }}
          variant="outlined"
          size="small"
          sx={{ my: 1 }}
          InputProps={{ sx: { fontWeight: 'bold' } }}
          placeholder="ex) http://localhost:8080"
          defaultValue={url}
          onChange={handleUrlChange}
        />
      </Grid>
      <Grid item xs={2} sx={{ marginY: 'auto' }}>
        <Typography fontFamily="Noto Sans KR" fontSize={20}>
          Proxy pass Path
        </Typography>
      </Grid>
      <Grid item xs={9}>
        <TextField
          fullWidth
          label="Proxy pass Path"
          InputLabelProps={{
            sx: { color: 'rgb(200,200,200)' },
          }}
          variant="outlined"
          size="small"
          sx={{ my: 1 }}
          InputProps={{ sx: { fontWeight: 'bold' } }}
          placeholder="ex) /api"
          defaultValue={path}
          onChange={handlePathChange}
        />
      </Grid>
      <Grid item xs={1} sx={{ marginY: 'auto' }}>
        <Button
          variant="outlined"
          startIcon={<DeleteIcon />}
          size="small"
          onClick={handleProxyDelClick}
          sx={{
            color: 'red',
            borderColor: 'red',
            my: 1,
          }}
        >
          삭제
        </Button>
      </Grid>
    </Grid>
  );
}
