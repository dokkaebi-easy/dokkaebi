import React, { useState } from 'react';
import Grid from '@mui/material/Grid';
import Typography from '@mui/material/Typography';
import TextField from '@mui/material/TextField';

interface NginxProps {
  value: string;
  index: number;
  domainValue: string[];
}

export default function DomainBox({ value, index, domainValue }: NginxProps) {
  const [domain, setDomain] = useState(value);

  const handleDomainChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setDomain(event.target.value);
    domainValue[index] = event.target.value;
  };

  return (
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
          defaultValue={domain}
          onChange={handleDomainChange}
        />
      </Grid>
    </Grid>
  );
}
